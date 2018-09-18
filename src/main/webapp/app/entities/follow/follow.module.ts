import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterPress08SharedModule } from 'app/shared';
import {
    FollowComponent,
    FollowingComponent,
    FollowerComponent,
    FollowDetailComponent,
    FollowUpdateComponent,
    FollowDeletePopupComponent,
    FollowDeleteDialogComponent,
    followRoute,
    followPopupRoute
} from './';

const ENTITY_STATES = [...followRoute, ...followPopupRoute];

@NgModule({
    imports: [JhipsterPress08SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FollowComponent, FollowingComponent, FollowerComponent, FollowDetailComponent, FollowUpdateComponent, FollowDeleteDialogComponent, FollowDeletePopupComponent],
    entryComponents: [FollowComponent, FollowingComponent, FollowerComponent, FollowUpdateComponent, FollowDeleteDialogComponent, FollowDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterPress08FollowModule {}
