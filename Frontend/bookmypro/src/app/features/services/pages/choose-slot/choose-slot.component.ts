import { Component, OnInit } from '@angular/core';

export interface TimeSlot {
  time: string;
  status: 'available' | 'selected' | 'taken';
  period: 'Morning' | 'Afternoon' | 'Evening';
}

export interface CalendarDay {
  dayNumber: number;
  isMuted: boolean;
  isAvailable: boolean;
  isSelected: boolean;
  hasDot: boolean;
  dateStr: string;
  fullDate: Date;
}

@Component({
  selector: 'app-choose-slot',
  templateUrl: './choose-slot.component.html',
  styleUrl: './choose-slot.component.css'
})
export class ChooseSlotComponent implements OnInit {
  currentMonthName = 'February 2026';
  currentMonthIndex = 1; // Feb (0-indexed is 1)
  currentYear = 2026;

  daysOfWeek = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
  calendarDays: CalendarDay[] = [];

  selectedDate: Date = new Date(2026, 1, 8); // Feb 8, 2026
  selectedTimeSlot = '10:00 AM';

  timeSlots: TimeSlot[] = [
    // Morning
    { time: '8:00 AM', status: 'available', period: 'Morning' },
    { time: '9:00 AM', status: 'available', period: 'Morning' },
    { time: '10:00 AM', status: 'selected', period: 'Morning' },
    { time: '11:00 AM', status: 'taken', period: 'Morning' },
    // Afternoon
    { time: '12:00 PM', status: 'available', period: 'Afternoon' },
    { time: '1:00 PM', status: 'available', period: 'Afternoon' },
    { time: '2:00 PM', status: 'taken', period: 'Afternoon' },
    { time: '3:00 PM', status: 'available', period: 'Afternoon' },
    // Evening
    { time: '4:00 PM', status: 'available', period: 'Evening' },
    { time: '5:00 PM', status: 'available', period: 'Evening' },
    { time: '6:00 PM', status: 'available', period: 'Evening' },
    { time: '7:00 PM', status: 'taken', period: 'Evening' }
  ];

  servicePrice = 599;
  visitCharge = 49;
  taxRate = 0.0534; // roughly 32 INR on 599

  get taxes(): number {
    return Math.round(this.servicePrice * this.taxRate);
  }

  get total(): number {
    return this.servicePrice + this.visitCharge + this.taxes;
  }

  ngOnInit(): void {
    this.generateCalendar();
  }

  generateCalendar(): void {
    // Generate February 2026 calendar matching the mockup HTML layout
    // Row 1: Jan 26 - Jan 31 (muted), Feb 1 (available)
    // Feb 2026 starts on Sunday, Feb 1st.
    
    const days: CalendarDay[] = [];

    // Jan padding days
    const janPadding = [26, 27, 28, 29, 30, 31];
    janPadding.forEach(dayNum => {
      days.push({
        dayNumber: dayNum,
        isMuted: true,
        isAvailable: false,
        isSelected: false,
        hasDot: false,
        dateStr: `Jan ${dayNum}`,
        fullDate: new Date(2026, 0, dayNum)
      });
    });

    // Feb days
    for (let d = 1; d <= 28; d++) {
      const dateObj = new Date(2026, 1, d);
      const dayOfWeek = dateObj.getDay(); // 0 is Sunday
      const isSunday = dayOfWeek === 0;

      const isSelected = d === 8;

      days.push({
        dayNumber: d,
        isMuted: isSunday,
        isAvailable: !isSunday,
        isSelected: isSelected,
        hasDot: !isSunday,
        dateStr: this.formatDateStr(dateObj),
        fullDate: dateObj
      });
    }

    // March padding day
    days.push({
      dayNumber: 1,
      isMuted: true,
      isAvailable: false,
      isSelected: false,
      hasDot: false,
      dateStr: 'Mar 1',
      fullDate: new Date(2026, 2, 1)
    });

    this.calendarDays = days;
  }

  formatDateStr(date: Date): string {
    const days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    return `${days[date.getDay()]}, ${months[date.getMonth()]} ${date.getDate()}`;
  }

  selectDay(day: CalendarDay): void {
    if (!day.isAvailable || day.isMuted) return;

    this.calendarDays.forEach(d => d.isSelected = false);
    day.isSelected = true;
    this.selectedDate = day.fullDate;
  }

  selectSlot(slot: TimeSlot): void {
    if (slot.status === 'taken') return;

    this.timeSlots.forEach(s => {
      if (s.status === 'selected') {
        s.status = 'available';
      }
    });
    slot.status = 'selected';
    this.selectedTimeSlot = slot.time;
  }

  getSlotsByPeriod(period: 'Morning' | 'Afternoon' | 'Evening'): TimeSlot[] {
    return this.timeSlots.filter(s => s.period === period);
  }

  getSelectedDateFormatted(): string {
    const options: Intl.DateTimeFormatOptions = { weekday: 'short', month: 'short', day: 'numeric', year: 'numeric' };
    return this.selectedDate.toLocaleDateString('en-US', options);
  }

  getSelectedDateShortFormatted(): string {
    const options: Intl.DateTimeFormatOptions = { weekday: 'short', month: 'short', day: 'numeric' };
    return this.selectedDate.toLocaleDateString('en-US', options);
  }
}
