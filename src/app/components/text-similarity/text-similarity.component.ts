import { Component } from '@angular/core';
import { Service } from 'src/app/services/service.service';
import { environment } from 'src/app/environments/environment';

@Component({
  selector: 'app-text-similarity',
  templateUrl: './text-similarity.component.html',
  styleUrls: ['./text-similarity.component.css']
})
export class TextSimilarityComponent {
  text1: string = '';
  text2: string = '';
  result: number | null = null;

  constructor(private service: Service) { }

  check() {
    const token = localStorage.getItem('token');
    const apiUrl = environment.apiSimilarity;

    const params: any = {
      text1: this.text1,
      text2: this.text2,
      token: token
    };

    this.service.getResults(apiUrl, params).subscribe((result: any) => {
      this.result = result.similarity;
    });
  }
}
