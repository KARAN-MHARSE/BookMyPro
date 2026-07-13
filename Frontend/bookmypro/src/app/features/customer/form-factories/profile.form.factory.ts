import { Injectable } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Injectable({
    providedIn: 'root'
})
export class ProfileFormFactory {

    constructor(private fb: FormBuilder) { }

    create() {
        return this.fb.group({

            personalInfo: this.fb.group({
                profilePhoto: this.fb.control<string | null>(null),
                firstName: [
                    'Jane',
                    Validators.required
                ],

                middleName: [''],

                lastName: [
                    'Doe',
                    Validators.required
                ],

                dob: [
                    '1993-08-14',
                    Validators.required
                ],

                gender: [
                    'Male',
                    Validators.required
                ],

                language: [
                    'English',
                    Validators.required
                ],

                occupation: ['Product Designer'],
                bio: [
                    'Homeowner in Austin. I love plants, clean floors, and dogs.'
                ]
            }),

            address: this.fb.group({

                addressId: [{
                    value: 'ADR-2019-77BC',
                    disabled: true
                }],

                customerId: [{
                    value: 'CUS-000241',
                    disabled: true
                }],

                addressType: [
                    'HOME',
                    Validators.required
                ],

                addressName: [
                    'Home',
                    Validators.required
                ],

                landmark: [
                    'Opposite Blue Bottle Coffee'
                ],

                addressLine1: [
                    '2410 South Lamar Blvd',
                    Validators.required
                ],

                addressLine2: [
                    'Apt 302'
                ],

                city: [
                    'Austin',
                    Validators.required
                ],

                district: [
                    'Travis'
                ],

                state: [
                    'Texas',
                    Validators.required
                ],

                country: [
                    'United States',
                    Validators.required
                ],

                postalCode: [
                    '78704',
                    Validators.required
                ],

                defaultAddress: [
                    true
                ],

                latitude: [
                    '30.2519'
                ],

                longitude: [
                    '-97.7684'
                ]

            }),

            documents: this.fb.group({
                aadhaar: [''],
                pan: ['']
            })

        });
    }

}