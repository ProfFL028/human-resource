import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SystemPosition} from "../system-position.entity";
import {SystemPositionService} from "../system-position.service";
import {DynamicDialogRef} from "primeng";

@Component({
  selector: 'app-system-position-detail',
  templateUrl: './system-position-detail.component.html',
  styleUrls: ['./system-position-detail.component.css']
})
export class SystemPositionDetailComponent implements OnInit {
  @Input() systemPosition: SystemPosition;

  @Output() onSubmitClick = new EventEmitter<SystemPosition>();
  @Output() onCancelClick = new EventEmitter();

  constructor(private systemPositionService: SystemPositionService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.systemPositionService.save(this.systemPosition);
    this.onSubmitClick.emit(this.systemPosition);
  }

  onCancel() {
    this.onCancelClick.emit(this.systemPosition);
  }

}
