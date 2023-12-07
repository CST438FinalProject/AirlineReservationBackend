package com.cst438.airlinereservation.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class ExtFlight {
    private String airline;
    private int flightNumber;
    private String suffix;

    @JsonFormat(shape =  JsonFormat.Shape.OBJECT)
    private PeriodOfOperation periodOfOperationUTC;

    @JsonFormat(shape =  JsonFormat.Shape.OBJECT)
    private PeriodOfOperation periodOfOperationLT;

    private List<Leg> legs;
    private List<DataElement> dataElements;


    static class PeriodOfOperation{
        private  String startDate;
        private String endDate;
        private String daysOfOperation;

        public PeriodOfOperation() {
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getDaysOfOperation() {
            return daysOfOperation;
        }

        public void setDaysOfOperation(String daysOfOperation) {
            this.daysOfOperation = daysOfOperation;
        }
    }


    static class Leg{
        private int sequenceNumber;
        private String origin;
        private String destination;
        private String serviceType;
        private String aircraftOwner;
        private String aircraftType;
        private String aircraftConfigurationVersion;
        private String registration;
        private boolean op;
        private int aircraftDepartureTimeUTC;
        private int aircraftDepartureTimeDateDiffUTC;
        private int aircraftDepartureTimeLT;
        private int aircraftDepartureTimeDateDiffLT;
        private int aircraftDepartureTimeVariation;
        private int aircraftArrivalTimeUTC;
        private int aircraftArrivalTimeDateDiffUTC;
        private int aircraftArrivalTimeLT;
        private int aircraftArrivalTimeDateDiffLT;
        private int aircraftArrivalTimeVariation;

        public Leg() {
        }

        public int getSequenceNumber() {
            return sequenceNumber;
        }

        public void setSequenceNumber(int sequenceNumber) {
            this.sequenceNumber = sequenceNumber;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getAircraftOwner() {
            return aircraftOwner;
        }

        public void setAircraftOwner(String aircraftOwner) {
            this.aircraftOwner = aircraftOwner;
        }

        public String getAircraftType() {
            return aircraftType;
        }

        public void setAircraftType(String aircraftType) {
            this.aircraftType = aircraftType;
        }

        public String getAircraftConfigurationVersion() {
            return aircraftConfigurationVersion;
        }

        public void setAircraftConfigurationVersion(String aircraftConfigurationVersion) {
            this.aircraftConfigurationVersion = aircraftConfigurationVersion;
        }

        public String getRegistration() {
            return registration;
        }

        public void setRegistration(String registration) {
            this.registration = registration;
        }

        public boolean isOp() {
            return op;
        }

        public void setOp(boolean op) {
            this.op = op;
        }

        public int getAircraftDepartureTimeUTC() {
            return aircraftDepartureTimeUTC;
        }

        public void setAircraftDepartureTimeUTC(int aircraftDepartureTimeUTC) {
            this.aircraftDepartureTimeUTC = aircraftDepartureTimeUTC;
        }

        public int getAircraftDepartureTimeDateDiffUTC() {
            return aircraftDepartureTimeDateDiffUTC;
        }

        public void setAircraftDepartureTimeDateDiffUTC(int aircraftDepartureTimeDateDiffUTC) {
            this.aircraftDepartureTimeDateDiffUTC = aircraftDepartureTimeDateDiffUTC;
        }

        public int getAircraftDepartureTimeLT() {
            return aircraftDepartureTimeLT;
        }

        public void setAircraftDepartureTimeLT(int aircraftDepartureTimeLT) {
            this.aircraftDepartureTimeLT = aircraftDepartureTimeLT;
        }

        public int getAircraftDepartureTimeDateDiffLT() {
            return aircraftDepartureTimeDateDiffLT;
        }

        public void setAircraftDepartureTimeDateDiffLT(int aircraftDepartureTimeDateDiffLT) {
            this.aircraftDepartureTimeDateDiffLT = aircraftDepartureTimeDateDiffLT;
        }

        public int getAircraftDepartureTimeVariation() {
            return aircraftDepartureTimeVariation;
        }

        public void setAircraftDepartureTimeVariation(int aircraftDepartureTimeVariation) {
            this.aircraftDepartureTimeVariation = aircraftDepartureTimeVariation;
        }

        public int getAircraftArrivalTimeUTC() {
            return aircraftArrivalTimeUTC;
        }

        public void setAircraftArrivalTimeUTC(int aircraftArrivalTimeUTC) {
            this.aircraftArrivalTimeUTC = aircraftArrivalTimeUTC;
        }

        public int getAircraftArrivalTimeDateDiffUTC() {
            return aircraftArrivalTimeDateDiffUTC;
        }

        public void setAircraftArrivalTimeDateDiffUTC(int aircraftArrivalTimeDateDiffUTC) {
            this.aircraftArrivalTimeDateDiffUTC = aircraftArrivalTimeDateDiffUTC;
        }

        public int getAircraftArrivalTimeLT() {
            return aircraftArrivalTimeLT;
        }

        public void setAircraftArrivalTimeLT(int aircraftArrivalTimeLT) {
            this.aircraftArrivalTimeLT = aircraftArrivalTimeLT;
        }

        public int getAircraftArrivalTimeDateDiffLT() {
            return aircraftArrivalTimeDateDiffLT;
        }

        public void setAircraftArrivalTimeDateDiffLT(int aircraftArrivalTimeDateDiffLT) {
            this.aircraftArrivalTimeDateDiffLT = aircraftArrivalTimeDateDiffLT;
        }

        public int getAircraftArrivalTimeVariation() {
            return aircraftArrivalTimeVariation;
        }

        public void setAircraftArrivalTimeVariation(int aircraftArrivalTimeVariation) {
            this.aircraftArrivalTimeVariation = aircraftArrivalTimeVariation;
        }
    }

    static class DataElement{
        private int startLegSequenceNumber;
        private int endLegSequenceNumber;
        private int id;
        private String value;

        public DataElement() {
        }

        public int getStartLegSequenceNumber() {
            return startLegSequenceNumber;
        }

        public void setStartLegSequenceNumber(int startLegSequenceNumber) {
            this.startLegSequenceNumber = startLegSequenceNumber;
        }

        public int getEndLegSequenceNumber() {
            return endLegSequenceNumber;
        }

        public void setEndLegSequenceNumber(int endLegSequenceNumber) {
            this.endLegSequenceNumber = endLegSequenceNumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public PeriodOfOperation getPeriodOfOperationUTC() {
        return periodOfOperationUTC;
    }

    public void setPeriodOfOperationUTC(PeriodOfOperation periodOfOperationUTC) {
        this.periodOfOperationUTC = periodOfOperationUTC;
    }

    public PeriodOfOperation getPeriodOfOperationLT() {
        return periodOfOperationLT;
    }

    public void setPeriodOfOperationLT(PeriodOfOperation periodOfOperationLocalTime) {
        this.periodOfOperationLT = periodOfOperationLT;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public List<DataElement> getDataElements() {
        return dataElements;
    }

    public void setDataElements(List<DataElement> dataElements) {
        this.dataElements = dataElements;
    }

    public Flight externalToFlight(){
        Flight f = new Flight();
        LocalDate ld = LocalDate.now();
        f.setCapacity(1);
        f.setCode(this.getAirline() + this.getFlightNumber());
        f.setDst(this.getLegs().get(this.getLegs().size()-1).destination);
        f.setSrc(this.getLegs().get(0).origin);
        f.setDepartureTime(Date.valueOf(ld));
        f.setArrivalTime(Date.valueOf(ld));
//        f.setArrivalTime(Date.valueOf(this.periodOfOperationUTC.getStartDate()));
//        f.setDepartureTime(Date.valueOf(this.periodOfOperationUTC.getEndDate()));
        f.setAvailableSeats(1);
        return f;
    }
}


