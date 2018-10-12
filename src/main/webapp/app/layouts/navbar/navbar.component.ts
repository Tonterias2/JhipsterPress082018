import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { VERSION } from 'app/app.constants';
import { JhiLanguageHelper, Principal, LoginModalService, LoginService } from 'app/core';
import { ProfileService } from '../profiles/profile.service';
import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from '../.././../app/entities/notification/notification.service';
import { IMessage } from 'app/shared/model/message.model';
import { MessageService } from '../.././../app/entities/message/message.service';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.css']
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    currentAccount: any;
    loginName: string;

    numberOfNotifications: number;
    numberOfMessages: number;

    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private notificationService: NotificationService,
        private messageService: MessageService,
        private jhiAlertService: JhiAlertService,
        private router: Router
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.loginName = this.currentAccount.login;
            console.log('CONSOLOG: M:ngOnInit & O: this.loginName : ', this.loginName);
            this.notifications().subscribe(
                    (res: HttpResponse<INotification[]>) => {
                        console.log('CONSOLOG: M:ngOnInit & O: notifications.res.body : ', res.body);
                        console.log('CONSOLOG: M:ngOnInit & O: notifications.res.body.length : ', res.body.length);
                        this.numberOfNotifications = res.body.length;
                        return this.numberOfNotifications;
                    },
//                    (res: HttpErrorResponse) => this.onError(res.message)
            );
            this.messages().subscribe(
                    (res: HttpResponse<IMessage[]>) => {
                        console.log('CONSOLOG: M:ngOnInit & O: messages.res.body : ', res.body);
                        this.numberOfMessages = res.body.length;
                        return this.numberOfMessages;
                    },
//                    (res: HttpErrorResponse) => this.onError(res.message)
            );
        });
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
    }

    changeLanguage(languageKey: string) {
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }

    private notifications() {
        const query = {
            };
        if ( this.currentAccount.id  != null) {
            query['userId.equals'] = this.currentAccount.id;
            query['isDeliverd.equals'] = 'false';
        }
        return this.notificationService.query(query);
    }

    private messages() {
        const query = {
            };
        if ( this.currentAccount.id  != null) {
            query['userId.equals'] = this.currentAccount.id;
            query['isDeliverd.equals'] = 'false';
        }
        return this.messageService.query(query);
    }
}
