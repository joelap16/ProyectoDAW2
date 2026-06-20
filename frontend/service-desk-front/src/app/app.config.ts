import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { jwtInterceptor } from './interceptors/jwt-interceptor';

import { provideRouter } from '@angular/router';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    
    provideHttpClient(
      withInterceptors([jwtInterceptor])
    ),

    provideRouter(routes)
  ]
};
