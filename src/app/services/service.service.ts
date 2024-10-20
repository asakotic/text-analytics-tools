import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HistoryService } from './history.service';

@Injectable({
  providedIn: 'root'
})
export class Service {

  constructor(private http: HttpClient, private historyService: HistoryService) { }

  getResults(url:string, params: any): Observable<any> {
    this.historyService.addRecord(url,params)
    return this.http.get(url, { params: params })
  }
}

