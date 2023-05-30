import { Component, OnInit } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';

@Component({
  selector: 'app-charts-page',
  templateUrl: './charts-page.component.html',
  styleUrls: ['./charts-page.component.scss']
})
export class ChartsPageComponent implements OnInit {

  selectedReport: string = this.configService.CASH_CREDIT_REPORT;
  selectedTabIndex: number = 0;

  constructor(private configService: ConfigService) { }

  ngOnInit(): void {
  }

   tabChanged(tabChangeEvent: MatTabChangeEvent): void {
    if (tabChangeEvent.index === 0) {
      this.selectedReport = this.configService.CASH_CREDIT_REPORT;
      this.selectedTabIndex = 0;
    } else if (tabChangeEvent.index === 1) {
      this.selectedReport = this.configService.CREDIT_CARD_REPORT;
      this.selectedTabIndex = 1;
    } else if (tabChangeEvent.index === 2) {
      this.selectedReport = this.configService.OVERDRAFT_REPORT;
      this.selectedTabIndex = 2;
    }
  }

}
