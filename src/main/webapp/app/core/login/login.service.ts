import { Injectable } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

import { Principal } from '../auth/principal.service';
import { AuthServerProvider } from '../auth/auth-session.service';

import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable({ providedIn: 'root' })
export class LoginService {

    private loginStatus = new BehaviorSubject<boolean>( false );
    public loginCast = this.loginStatus.asObservable();

    constructor(
        private languageService: JhiLanguageService,
        private principal: Principal,
        private authServerProvider: AuthServerProvider
    ) { }

    editLoginStatus( status ) {
        this.loginStatus.next( status );
    }

    login(credentials, callback?) {
        const cb = callback || function() {};

        return new Promise((resolve, reject) => {
            this.authServerProvider.login(credentials).subscribe(
                data => {
                    this.principal.identity(true).then(account => {
                        // After the login the language will be changed to
                        // the language selected by the user during his registration
                        if (account !== null) {
                            this.languageService.changeLanguage(account.langKey);
                        }
                        resolve(data);
                    });
                    return cb();
                },
                err => {
                    this.logout();
                    reject(err);
                    return cb(err);
                }
            );
        });
    }

    logout() {
        this.authServerProvider.logout().subscribe();
        this.principal.authenticate(null);
    }
}
