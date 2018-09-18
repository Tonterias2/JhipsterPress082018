import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterPress08SharedModule } from 'app/shared';
import { JhipsterPress08AdminModule } from 'app/admin/admin.module';
import {
    NotificationComponent,
    NotificationDetailComponent,
    NotificationUpdateComponent,
    NotificationDeletePopupComponent,
    NotificationDeleteDialogComponent,
    notificationRoute,
    notificationPopupRoute
} from './';

const ENTITY_STATES = [...notificationRoute, ...notificationPopupRoute];

@NgModule({
    imports: [JhipsterPress08SharedModule, JhipsterPress08AdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NotificationComponent,
        NotificationDetailComponent,
        NotificationUpdateComponent,
        NotificationDeleteDialogComponent,
        NotificationDeletePopupComponent
    ],
    entryComponents: [
        NotificationComponent,
        NotificationUpdateComponent,
        NotificationDeleteDialogComponent,
        NotificationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterPress08NotificationModule {}
