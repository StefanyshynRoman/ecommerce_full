package com.rstefanyshyn.ecom.order.infrastructure.primary.order;

import com.rstefanyshyn.ecom.order.application.OrderApplicationService;
import com.rstefanyshyn.ecom.order.domain.order.CartPaymentException;
import com.rstefanyshyn.ecom.order.domain.order.aggregate.*;
import com.rstefanyshyn.ecom.order.domain.order.vo.StripeSessionId;
import com.rstefanyshyn.ecom.order.domain.user.vo.*;
import com.rstefanyshyn.ecom.product.domain.vo.PublicId;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Address;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/orders")
public class OrderResource {

  private final OrderApplicationService orderApplicationService;


  @Value("${application.stripe.secret-key}")
  private String stripeSecretKey;

  @Value("${application.stripe.webhook-secret}")
  private String webhookSecret;

  public OrderResource(OrderApplicationService orderApplicationService) {
    this.orderApplicationService = orderApplicationService;
  }

  @GetMapping("/get-cart-details")
  public ResponseEntity<RestDetailCartResponse> getDetails(@RequestParam List<UUID> productIds) {
    List<DetailCartItemRequest> cartItemRequests = productIds.stream()
      .map(uuid -> new DetailCartItemRequest(new PublicId(uuid), 1))
      .toList();

    DetailCartRequest detailCartRequest = DetailCartRequestBuilder.detailCartRequest()
      .items(cartItemRequests)
      .build();
    DetailCartResponse cartDetails = orderApplicationService.getCartDetails(detailCartRequest);
    return ResponseEntity.ok(RestDetailCartResponse.from(cartDetails));
  }

  @PostMapping("/init-payment")
  public ResponseEntity<RestStripeSession> initPayment(@RequestBody List<RestCartItemRequest> items) {
    List<DetailCartItemRequest> detailCartItemRequests = RestCartItemRequest.to(items);
    try {
      com.stripe.Stripe.apiKey = stripeSecretKey;

      StripeSessionId stripeSessionInformation = orderApplicationService.createOrder(detailCartItemRequests);
      RestStripeSession restStripeSession = RestStripeSession.from(stripeSessionInformation);
      return ResponseEntity.ok(restStripeSession);
    } catch (CartPaymentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/webhook")
  public ResponseEntity<Void> webhookStripe(@RequestBody String payload,
                                            @RequestHeader("Stripe-Signature") String stripeSignature) {
    Event event;
    try {
      event = Webhook.constructEvent(payload, stripeSignature, webhookSecret);
    } catch (SignatureVerificationException e) {
      return ResponseEntity.badRequest().build();
    }

    Optional<StripeObject> rawStripeObjectOpt = event.getDataObjectDeserializer().getObject();

    if ("checkout.session.completed".equals(event.getType())) {
      handleCheckoutSessionCompleted(rawStripeObjectOpt.orElseThrow());
    }

    return ResponseEntity.ok().build();
  }

  private void handleCheckoutSessionCompleted(StripeObject rawStripeObject) {
    if (rawStripeObject instanceof Session session) {
      Address address = session.getCustomerDetails().getAddress();

      UserAddress userAddress = UserAddressBuilder.userAddress()
        .city(address.getCity())
        .country(address.getCountry())
        .zipCode(address.getPostalCode())
        .street(address.getLine1())
        .build();

      UserAddressToUpdate userAddressToUpdate = UserAddressToUpdateBuilder.userAddressToUpdate()
        .userAddress(userAddress)
        .userPublicId(new UserPublicId(UUID.fromString(session.getMetadata().get("user_public_id"))))
        .build();

      StripeSessionInformation sessionInformation = StripeSessionInformationBuilder.stripeSessionInformation()
        .userAddress(userAddressToUpdate)
        .stripeSessionId(new StripeSessionId(session.getId()))
        .build();

      orderApplicationService.updateOrder(sessionInformation);
    }
  }
}

//  @GetMapping("/user")
//  public ResponseEntity<Page<RestOrderRead>> getOrdersForConnectedUser(Pageable pageable) {
//    Page<Order> orders = orderApplicationService.findOrdersForConnectedUser(pageable);
//    PageImpl<RestOrderRead> restOrderReads = new PageImpl<>(
//      orders.getContent().stream().map(RestOrderRead::from).toList(),
//      pageable,
//      orders.getTotalElements()
//    );
//    return ResponseEntity.ok(restOrderReads);
//  }
//
//  @GetMapping("/admin")
//  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
//  public ResponseEntity<Page<RestOrderReadAdmin>> getOrdersForAdmin(Pageable pageable) {
//    Page<Order> orders = orderApplicationService.findOrdersForAdmin(pageable);
//    PageImpl<RestOrderReadAdmin> restOrderReads = new PageImpl<>(
//      orders.getContent().stream().map(RestOrderReadAdmin::from).toList(),
//      pageable,
//      orders.getTotalElements()
//    );
//    return ResponseEntity.ok(restOrderReads);
//  }
