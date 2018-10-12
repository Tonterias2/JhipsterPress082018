import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterPress08SharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent, ABOUT_ROUTE, ERROR404_ROUTE, HELP_ROUTE, PRIVACY_ROUTE, TERMS_ROUTE, CLIENTS_ROUTE, COMMINGSOON_ROUTE } from './';
import { AboutComponent } from '../static/about/about.component';
import { ErrorComponent } from 'app/static/error/error.component';
import { HelpComponent } from 'app/static/help/help.component';
import { PrivacyComponent } from 'app/static/privacy/privacy.component';
import { TermsComponent } from 'app/static/terms/terms.component';
import { ClientsComponent } from 'app/static/clients/clients.component';
import { CommingsoonComponent } from 'app/static/commingsoon/commingsoon.component';

@NgModule({
    imports: [
              JhipsterPress08SharedModule,
              RouterModule.forChild([
                                     HOME_ROUTE,
                                     ABOUT_ROUTE,
                                     ERROR404_ROUTE,
                                     HELP_ROUTE,
                                     PRIVACY_ROUTE,
                                     TERMS_ROUTE,
                                     CLIENTS_ROUTE,
                                     COMMINGSOON_ROUTE
                                     ])
              ],
    declarations: [
                   HomeComponent,
                   AboutComponent,
                   ErrorComponent,
                   HelpComponent,
                   PrivacyComponent,
                   TermsComponent,
                   ClientsComponent,
                   CommingsoonComponent
                   ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterPress08HomeModule {}
