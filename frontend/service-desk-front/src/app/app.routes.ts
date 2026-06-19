import { Routes } from '@angular/router';
import { Login } from './pages/auth/login/login';
import { Register } from './pages/auth/register/register';
import { DashboardAdmin } from './pages/admin/dashboard/dashboard';
import { DashboardTecnico} from './pages/tecnico/dashboard/dashboard';
import { DashboardUsuario } from './pages/usuario/dashboard/dashboard';

export const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },

  { path: 'auth/login', component: Login },
  { path: 'auth/register', component: Register },
  
  { path: 'admin/dashboard', component: DashboardAdmin },
  { path: 'tecnico/dashboard', component: DashboardTecnico },
  { path: 'usuario/dashboard', component: DashboardUsuario }
];