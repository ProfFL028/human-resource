import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  collapsed: boolean;
  isActive: boolean;
  showMenu: string;

  @Output() collapsedEvent = new EventEmitter<boolean>();

  constructor(public router: Router) { }

  ngOnInit() {
    this.isActive = false;
    this.collapsed = false;
    this.showMenu = '';
  }
  addExpandClass(element: any) {
    if (element === this.showMenu) {
      this.showMenu = '0';
    } else {
      this.showMenu = element;
    }
  }

  toggleCollapsed() {
    this.collapsed = !this.collapsed;
    this.collapsedEvent.emit(this.collapsed);
  }

  onLoggedout() {

  }

}
