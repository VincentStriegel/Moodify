import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BackendCommunicationService } from '../services/backend-communication.service';

@Component({
    selector: 'app-login-register',
    templateUrl: './login-register.component.html',
    styleUrls: ['./login-register.component.css'],
})
export class LoginRegisterComponent implements OnInit {
    loginForm!: FormGroup;
    registerForm!: FormGroup;
    login: boolean = true;
    email = new FormControl('', [Validators.required, Validators.email]);
    username = new FormControl('', [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]'),
    ]);
    password = new FormControl('', [
        Validators.required,
        Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{8,}$'),
    ]);
    responseError: string = '';

    getErrorMessageEmail() {
        if (this.email.hasError('required')) {
            return 'You must enter a value';
        }
        return this.email.hasError('email') ? 'Not a valid email' : '';
    }

    getErrorMessageUsername() {
        if (this.username.hasError('required')) {
            return 'You must enter a value';
        }

        if (this.username.hasError('pattern')) {
            return 'Username: 5-20 characters, alphanumeric, dot, underscore, hyphen. No dots, underscores, or hyphens at start/end or in a row.';
        }

        return '';
    }

    getErrorMessagePassword() {
        if (this.password.hasError('required')) {
            return 'You must enter a value';
        }

        if (this.password.hasError('pattern')) {
            return 'Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one special character.';
        }

        return '';
    }

    constructor(
        private formBuilder: FormBuilder,
        private backendCommunicationService: BackendCommunicationService,
    ) {}

    ngOnInit(): void {
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required],
        });

        this.registerForm = this.formBuilder.group({
            username: ['', Validators.required, Validators.length],
            email: ['', [Validators.required, Validators.email]],
            password: ['', Validators.required, Validators.length],
        });
    }

    onLogin(): void {
        const loginData = {
            username: this.username?.value as string,
            password: this.password?.value as string,
        };

        this.backendCommunicationService.login(loginData.username, loginData.password).subscribe(
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            (response) => {
                this.responseError = '';
            },
            (error) => {
                this.responseError = error.error.message;
            },
        );
    }

    onRegister(): void {
        const registerData = {
            username: this.username?.value as string,
            password: this.password?.value as string,
            email: this.email?.value as string,
        };

        this.backendCommunicationService
            .register(registerData.username, registerData.password, registerData.email)
            .subscribe(
                // eslint-disable-next-line @typescript-eslint/no-unused-vars
                (response) => {
                    this.responseError = '';
                },
                (error) => {
                    this.responseError = error.error.message;
                },
            );
    }
}
