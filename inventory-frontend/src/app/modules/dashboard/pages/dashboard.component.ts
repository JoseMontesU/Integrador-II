import { Component, OnInit } from '@angular/core';
import { UtilService } from '../../shared/services/util.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(
    private util: UtilService
  ) { }

  ngOnInit(): void {
    this.util.getToken();
  }

}
