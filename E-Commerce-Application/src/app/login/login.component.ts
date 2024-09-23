import { Component, HostListener } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm!:FormGroup;

  constructor(private fb:FormBuilder,private router:Router,private service:ApiService)
  {
    this.loginForm=this.fb.group({
      userName:['',[Validators.required]],
      password:['',[Validators.required]]
    })
  }
  signUp()
{
  this.router.navigateByUrl("/register");
}
onLogin()
{
this.service.login(this.loginForm.value.userName,this.loginForm.value.password).subscribe((res)=>{
  localStorage.removeItem('token');
  console.log(res);
  if (res) {
    console.log(res);
    localStorage.setItem('token', res);
    this.router.navigateByUrl("/products");
  }
},(err)=>{
  console.log(err);
})
}

}
