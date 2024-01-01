import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { Injectable, inject } from "@angular/core";
import { AuthService } from "../auth/auth.service";


@Injectable()
export class AuthGuardService {

    constructor(private router: Router,
        private authService: AuthService) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const expectedRoles = route.data['roles'];

        if (!this.authService.isAuthenticated()) {
            this.authService.logout();
            this.router.navigate(['/login']);
            return false;
        }

        if(!this.authService.hasAnyRole(expectedRoles)) {
            this.router.navigate(['/unauthorized']);
            return false;
        }
        return true;
    };
}

export const AuthGuard: CanActivateFn = ((next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
    return inject(AuthGuardService).canActivate(next, state);
})

