import { Routes } from '@angular/router';
import { Login } from './pages/auth/login/login';
import { Register } from './pages/auth/register/register';

import { DashboardAdmin } from './pages/admin/dashboard/dashboard';
import { DashboardTecnico } from './pages/tecnico/dashboard/dashboard';
import { DashboardUsuario } from './pages/usuario/dashboard/dashboard';

import { authGuard } from './guards/auth-guard';
import { roleGuard } from './guards/role-guard';

import { AdminLayout } from './layouts/admin-layout/admin-layout';
import { TecnicoLayout } from './layouts/tecnico-layout/tecnico-layout';
import { UsuarioLayout } from './layouts/usuario-layout/usuario-layout';

import { ListaTickets } from './pages/admin/lista-tickets/lista-tickets';

import { TecnicosAdmin } from './pages/admin/tecnicos-admin/tecnicos-admin';

import { UsuariosAdmin } from './pages/admin/usuarios-admin/usuarios-admin';

export const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },

  { path: 'auth/login', component: Login },
  { path: 'auth/register', component: Register },

  {
    path: 'admin',
    component: AdminLayout,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ADMINISTRADOR'] },
    children: [

      {
        path: 'dashboard',
        component: DashboardAdmin
      },

      { 
        path: 'tickets',
        component: ListaTickets
      },

      {
        path: 'tecnicos',
        component: TecnicosAdmin
      },

      {
        path: 'usuarios',
        component: UsuariosAdmin
      }
    ]
    
  },
  
  {
    path: 'tecnico',
    component: TecnicoLayout,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['TECNICO'] },
    children: [

      {
        path: 'dashboard',
        component: DashboardTecnico
      }

    ]

  },

  {
    path: 'usuario',
    component: UsuarioLayout,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['USUARIO'] },
    children: [

      {
        path: 'dashboard',
        component: DashboardUsuario
      }

    ]

  } 

];