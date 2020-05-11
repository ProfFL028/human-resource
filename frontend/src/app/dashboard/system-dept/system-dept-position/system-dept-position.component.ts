import { Component, OnInit } from '@angular/core';
import {SystemPosition} from '../../system-position/system-position.entity'

@Component({
  selector: 'app-system-dept-position',
  templateUrl: './system-dept-position.component.html',
  styleUrls: ['./system-dept-position.component.css']
})
export class SystemDeptPositionComponent implements OnInit {

  sourcePositions: SystemPosition[] = []
  targetPositions: SystemPosition[] = []

  constructor() { }

  ngOnInit(): void {
  }

}
