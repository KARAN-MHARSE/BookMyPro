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
                    '',
                    Validators.required
                ],

                middleName: [''],

                lastName: [
                    '',
                    Validators.required
                ],

                dob: [
                    '',
                    Validators.required
                ],

                gender: [
                    '',
                    Validators.required
                ],

                language: [
                    '',
                    Validators.required
                ],

                occupation: [''],
                bio: [
                    ''
                ]
            }),

            address: this.fb.group({

                addressId: [{
                    value: '',
                    disabled: true
                }],

                customerId: [{
                    value: '',
                    disabled: true
                }],

                addressType: [
                    'HOME',
                    Validators.required
                ],

                addressName: [
                    '',
                    Validators.required
                ],

                landmark: [
                    ''
                ],

                addressLine1: [
                    '',
                    Validators.required
                ],

                addressLine2: [
                    ''
                ],

                city: [
                    '',
                    Validators.required
                ],

                district: [
                    ''
                ],

                state: [
                    '',
                    Validators.required
                ],

                country: [
                    '',
                    Validators.required
                ],

                postalCode: [
                    '',
                    Validators.required
                ],

                defaultAddress: [
                    true
                ],

                latitude: [
                    ''
                ],

                longitude: [
                    ''
                ]

            }),

            documents: this.fb.group({
                aadhaar: [''],
                pan: ['']
            })

        });
    }

}