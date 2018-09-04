import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterPress08SharedModule } from 'app/shared';
import {
    UrllinkComponent,
    UrllinkDetailComponent,
    UrllinkUpdateComponent,
    UrllinkDeletePopupComponent,
    UrllinkDeleteDialogComponent,
    urllinkRoute,
    urllinkPopupRoute
} from './';

const ENTITY_STATES = [...urllinkRoute, ...urllinkPopupRoute];

@NgModule({
    imports: [JhipsterPress08SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UrllinkComponent,
        UrllinkDetailComponent,
        UrllinkUpdateComponent,
        UrllinkDeleteDialogComponent,
        UrllinkDeletePopupComponent
    ],
    entryComponents: [UrllinkComponent, UrllinkUpdateComponent, UrllinkDeleteDialogComponent, UrllinkDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterPress08UrllinkModule {}
