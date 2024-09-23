import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../api.service';
import { register } from '../model/register';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerForm!:FormGroup;


  constructor(private fb:FormBuilder,private service:ApiService,private router:Router)
  {
    this.registerForm= this.fb.group({
      userName:['',[Validators.required]],
      email:['',[Validators.required,Validators.email]],
      password:['',[Validators.required,Validators.minLength(8)]]
    })
  }

  onSubmit(){

    this.service.register(this.registerForm.value).subscribe((res)=>{
      console.log(res);
      this.router.navigateByUrl("/login");
    },
    (err)=>{
      console.log(err);
    })

  }
  onLogin(){
this.router.navigateByUrl("/login");
  }

}
