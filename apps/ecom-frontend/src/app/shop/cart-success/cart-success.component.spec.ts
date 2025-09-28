import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CartSuccesComponent } from './cart-success.component';

describe('CartSuccesComponent', () => {
  let component: CartSuccesComponent;
  let fixture: ComponentFixture<CartSuccesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CartSuccesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CartSuccesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
