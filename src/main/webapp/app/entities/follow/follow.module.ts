import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterPress08SharedModule } from 'app/shared';
import {
    FollowComponent,
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
    declarations: [FollowComponent, FollowDetailComponent, FollowUpdateComponent, FollowDeleteDialogComponent, FollowDeletePopupComponent],
    entryComponents: [FollowComponent, FollowUpdateComponent, FollowDeleteDialogComponent, FollowDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterPress08FollowModule {}
