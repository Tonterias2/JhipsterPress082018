import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterPress08SharedModule } from 'app/shared';
import {
    BlockuserComponent,
    BlockuserDetailComponent,
    BlockuserUpdateComponent,
    BlockuserDeletePopupComponent,
    BlockuserDeleteDialogComponent,
    blockuserRoute,
    blockuserPopupRoute
} from './';

const ENTITY_STATES = [...blockuserRoute, ...blockuserPopupRoute];

@NgModule({
    imports: [JhipsterPress08SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BlockuserComponent,
        BlockuserDetailComponent,
        BlockuserUpdateComponent,
        BlockuserDeleteDialogComponent,
        BlockuserDeletePopupComponent
    ],
    entryComponents: [BlockuserComponent, BlockuserUpdateComponent, BlockuserDeleteDialogComponent, BlockuserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterPress08BlockuserModule {}
