import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  private records: any[] = [];

  constructor() { }

  addRecord(url: string, params: any){
    const time = new Date()
    const queryParams = new URLSearchParams();
    for (const key in params) {
      if (params.hasOwnProperty(key)) {
        queryParams.set(key, params[key]);
      }
    }
    const method = "GET"
    const path = `${url}?${queryParams.toString()}`
    const obj = {
      time,
      method,
      path
    }
    
    this.records.push(obj);
    console.log(this.records)
  }
 
  getAllRecords(){
    console.log(this.records)
    return this.records
  }
}
