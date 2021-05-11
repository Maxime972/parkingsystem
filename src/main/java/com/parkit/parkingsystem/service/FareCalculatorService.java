package com.parkit.parkingsystem.service;

import java.time.Duration;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket, int nb) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}
		
		

		double inHour = ticket.getInTime().getTime();
		double outHour = ticket.getOutTime().getTime();
		
		System.out.println(inHour);
		System.out.println(outHour);


		// TODO: Some tests are failing here. Need to check if this logic is correct
		double duration =  (double)Math.round(((outHour - inHour)/1000/60/60)*100.0)/100.0;
		
		System.out.println("Temps dans le parking : " + duration + " heure(s)");

		// Gratuité des 30 minutes de parking
		if (duration < 0.5) {
			ticket.setPrice(0);
		} else {
			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				if (nb >= 1) {
					ticket.setPrice(Math.round(duration * Fare.CAR_RATE_PER_HOUR * 0.95*100.0)/100.0);
				} else {
					ticket.setPrice(Math.round(duration * Fare.CAR_RATE_PER_HOUR*100.0)/100.0);
				}
				break;
			}
			case BIKE: {
				ticket.setPrice(Math.round(duration * Fare.BIKE_RATE_PER_HOUR*100.0)/100.0);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
			}
		}
		System.out.println("Le prix est de : " + ticket.getPrice());

	}
}