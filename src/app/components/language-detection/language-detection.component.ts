import { Component } from '@angular/core';
import { environment } from 'src/app/environments/environment';
import { Service } from 'src/app/services/service.service';

@Component({
  selector: 'app-language-detection',
  templateUrl: './language-detection.component.html',
  styleUrls: ['./language-detection.component.css']
})
export class LanguageDetectionComponent {
  text: string = '';
  clean: boolean = false;
  results: any[] = [];

  constructor(private service: Service) { }

  detect() {
    const token = localStorage.getItem('token');

    const params: any = {
      text: this.text,
      token: token
    };

    if (this.clean) {
      params.clean = true;
    }
    const url = environment.apiDetection

    this.service.getResults(url, params).subscribe((results) => {
      this.results = results.detectedLangs
      console.log(results)
    })
  }
}
