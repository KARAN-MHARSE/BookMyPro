import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProviderRoutingModule } from './provider-routing.module';
import { ProviderProfilePage } from './pages/provider-profile/provider-profile.page';
import { SidebarComponent } from './pages/provider-profile/components/sidebar/sidebar.component';
import { ProfileSummaryComponent } from './pages/provider-profile/components/profile-summary/profile-summary.component';
import { PersonalInformationComponent } from './pages/provider-profile/components/personal-information/personal-information.component';
import { ProfessionalInformationComponent } from './pages/provider-profile/components/professional-information/professional-information.component';
import { EducationComponent } from './pages/provider-profile/components/education/education.component';
import { ExperienceComponent } from './pages/provider-profile/components/experience/experience.component';
import { DocumentsComponent } from './pages/provider-profile/components/documents/documents.component';
import { ServicesComponent } from './pages/provider-profile/components/services/services.component';
import { AvailabilityComponent } from './pages/provider-profile/components/availability/availability.component';
import { BankDetailsComponent } from './pages/provider-profile/components/bank-details/bank-details.component';
import { EducationDialogComponent } from './pages/provider-profile/components/education/education-dialog/education-dialog.component';
import { ExperienceDialogComponent } from './pages/provider-profile/components/experience/experience-dialog/experience-dialog.component';
import { ServiceDialogComponent } from './pages/provider-profile/components/services/service-dialog/service-dialog.component';
import { DocumentUploadDialogComponent } from './pages/provider-profile/components/documents/document-upload-dialog/document-upload-dialog.component';
import { AvailabilityDialogComponent } from './pages/provider-profile/components/availability/availability-dialog/availability-dialog.component';
import { PricingDialogComponent } from './pages/provider-profile/dialogs/pricing-dialog/pricing-dialog.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '@app/shared/shared.module';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    ProviderProfilePage,
    SidebarComponent,
    ProfileSummaryComponent,
    PersonalInformationComponent,
    ProfessionalInformationComponent,
    EducationComponent,
    ExperienceComponent,
    DocumentsComponent,
    ServicesComponent,
    AvailabilityComponent,
    BankDetailsComponent,
    EducationDialogComponent,
    ExperienceDialogComponent,
    ServiceDialogComponent,
    DocumentUploadDialogComponent,
    AvailabilityDialogComponent,
    PricingDialogComponent
  ],
  imports: [
    CommonModule,
    ProviderRoutingModule,
    ReactiveFormsModule,
    SharedModule,
    MatDialogModule
  ]
})
export class ProviderModule { }
