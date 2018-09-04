import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Community } from 'app/shared/model/community.model';
import { CommunityService } from './community.service';
import { CommunityComponent } from './community.component';
import { CommunityDetailComponent } from './community-detail.component';
import { CommunityUpdateComponent } from './community-update.component';
import { CommunityDeletePopupComponent } from './community-delete-dialog.component';
import { ICommunity } from 'app/shared/model/community.model';

@Injectable({ providedIn: 'root' })
export class CommunityResolve implements Resolve<ICommunity> {
    constructor(private service: CommunityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((community: HttpResponse<Community>) => community.body));
        }
        return of(new Community());
    }
}

export const communityRoute: Routes = [
    {
        path: 'community',
        component: CommunityComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterPress08App.community.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'community/:id/view',
        component: CommunityDetailComponent,
        resolve: {
            community: CommunityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterPress08App.community.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'community/new',
        component: CommunityUpdateComponent,
        resolve: {
            community: CommunityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterPress08App.community.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'community/:id/edit',
        component: CommunityUpdateComponent,
        resolve: {
            community: CommunityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterPress08App.community.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const communityPopupRoute: Routes = [
    {
        path: 'community/:id/delete',
        component: CommunityDeletePopupComponent,
        resolve: {
            community: CommunityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterPress08App.community.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
