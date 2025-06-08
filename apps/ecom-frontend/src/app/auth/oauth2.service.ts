import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Injectable({
  providedIn: 'root'
})
export class Oauth2Service {
http=inject(HttpClient);
oidcSecurityService=inject(OidcSecurityService)
  constructor() { }
}
