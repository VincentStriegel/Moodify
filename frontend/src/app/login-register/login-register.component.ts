import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BackendCommunicationService } from '../services/backend-communication.service';

@Component({
  selector: 'app-login-register',
  templateUrl: './login-register.component.html',
  styleUrls: ['./login-register.component.css']
})
export class LoginRegisterComponent implements OnInit {
  loginForm!: FormGroup;
  registerForm!: FormGroup;
  login: boolean = true;

  constructor(private formBuilder: FormBuilder, private backendCommunicationService: BackendCommunicationService) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onLogin(): void {
    // Implement your login logic here
  }

  onRegister(): void {
    const registerData = {
      username: this.registerForm.get('username')?.value,
      password: this.registerForm.get('password')?.value,
      email: this.registerForm.get('email')?.value
    };

    this.backendCommunicationService.register(registerData.username, registerData.password, registerData.username).subscribe((data) => {
    });
  }
}