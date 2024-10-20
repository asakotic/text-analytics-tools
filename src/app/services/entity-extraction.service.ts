import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { Entity } from '../models/entity'
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class Service {


  constructor(private http: HttpClient) { }

  getResults(url:string, params: any): Observable<any> {
    return this.http.get(url, { params: params })
  }
}

