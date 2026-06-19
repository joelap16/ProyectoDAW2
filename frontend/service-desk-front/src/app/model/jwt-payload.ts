export interface JwtPayload {
  
  sub: string; // correo
  rol: string;
  iat: number;
  exp: number;
}