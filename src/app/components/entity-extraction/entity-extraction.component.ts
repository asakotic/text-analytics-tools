import { Component } from '@angular/core';
import { environment } from 'src/app/environments/environment';
import { Service } from 'src/app/services/entity-extraction.service';

@Component({
  selector: 'app-entity-extraction',
  templateUrl: './entity-extraction.component.html',
  styleUrls: ['./entity-extraction.component.css']
})
export class EntityExtractionComponent {
  text: string = '';
  minConfidence: number = 0.5;  
  includeImage: boolean = false;  
  includeAbstract: boolean = false;  
  includeCategories: boolean = false; 
  results: any[] = []; 

  constructor(private service : Service) { }

  search() {

    const token = localStorage.getItem('token');

    const params: any = {
      text: this.text,
      min_confidence: this.minConfidence,
      token: token
    };

    let tmp: string[] = [];
    if (this.includeImage) tmp.push('image');
    if (this.includeAbstract) tmp.push('abstract');
    if (this.includeCategories) tmp.push('categories');

    if (tmp.length > 0) {
      params.include = tmp.join(',');
    }

    const url = environment.apiExtraction
    
    this.service.getResults(url,params).subscribe( (results) => {
       this.results = results.annotations
       console.log(results)
    })
  }
}

