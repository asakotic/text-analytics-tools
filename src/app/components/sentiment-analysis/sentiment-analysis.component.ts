import { Component } from '@angular/core';
import { environment } from 'src/app/environments/environment';
import { Service } from 'src/app/services/service.service';

@Component({
  selector: 'app-sentiment-analysis',
  templateUrl: './sentiment-analysis.component.html',
  styleUrls: ['./sentiment-analysis.component.css']
})
export class SentimentAnalysisComponent {
  text: string = '';  
  lang: string = 'auto';  
  result: any = null;  

  constructor(private service: Service) { }

  analyze() {
    const token = localStorage.getItem('token'); 
    const apiUrl = environment.apiAnalysis;

    const params: any = {
      text: this.text,
      token: token,
      lang: this.lang
    };


    this.service.getResults(apiUrl, params).subscribe((result: any) => {
      const score = result.sentiment.score;
      let type = result.sentiment.type;
      let color = '';

      if (score > 0) {
        color = 'green';
      } else if (score < 0) {
        color = 'red';
      } else {
        color = 'brown';
      }

      this.result = {
        score: score,
        type: type,
        color: color
      };
    });
  }
}
