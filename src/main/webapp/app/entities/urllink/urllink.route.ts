import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Urllink } from 'app/shared/model/urllink.model';
import { UrllinkService } from './urllink.service';
import { UrllinkComponent } from './urllink.component';
import { UrllinkDetailComponent } from './urllink-detail.component';
import { UrllinkUpdateComponent } from './urllink-update.component';
import { UrllinkDeletePopupComponent } from './urllink-delete-dialog.component';
import { IUrllink } from 'app/shared/model/urllink.model';

@Injectable({ providedIn: 'root' })
export class UrllinkResolve implements Resolve<IUrllink> {
    constructor(private service: UrllinkService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((urllink: HttpResponse<Urllink>) => urllink.body));
        }
        return of(new Urllink());
    }
}

export const urllinkRoute: Routes = [
    {
        path: 'urllink',
        component: UrllinkComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterPress08App.urllink.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'urllink/:id/view',
        component: UrllinkDetailComponent,
        resolve: {
            urllink: UrllinkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterPress08App.urllink.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'urllink/new',
        component: UrllinkUpdateComponent,
        resolve: {
            urllink: UrllinkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterPress08App.urllink.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'urllink/:id/edit',
        component: UrllinkUpdateComponent,
        resolve: {
            urllink: UrllinkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterPress08App.urllink.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const urllinkPopupRoute: Routes = [
    {
        path: 'urllink/:id/delete',
        component: UrllinkDeletePopupComponent,
        resolve: {
            urllink: UrllinkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterPress08App.urllink.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
