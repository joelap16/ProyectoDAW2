import { inject } from '@angular/core';
import { CanActivateFn, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

export const roleGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot
) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const tokenValido = authService.isLoggedIn();
  if (!tokenValido) {
    return router.parseUrl('/auth/login');
  }

  const rolUsuario = authService.getRol();
  const rolesPermitidos = route.data['roles'] as string[] | undefined;

  if (!rolesPermitidos || !rolUsuario || !rolesPermitidos.includes(rolUsuario)) {
    return router.parseUrl('/auth/login');
  }

  return true;
};