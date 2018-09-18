import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterPress08SharedModule } from 'app/shared';
import {
    TagComponent,
    TagDetailComponent,
    TagUpdateComponent,
    TagDeletePopupComponent,
    TagDeleteDialogComponent,
    tagRoute,
    tagPopupRoute
} from './';

const ENTITY_STATES = [...tagRoute, ...tagPopupRoute];

@NgModule({
    imports: [JhipsterPress08SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TagComponent, TagDetailComponent, TagUpdateComponent, TagDeleteDialogComponent, TagDeletePopupComponent],
    entryComponents: [TagComponent, TagUpdateComponent, TagDeleteDialogComponent, TagDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterPress08TagModule {}
