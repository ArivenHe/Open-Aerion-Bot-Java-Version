package com.ariven.service;

import com.ariven.vo.MetarVO;
import io.github.mivek.exception.ParseException;

public interface IWeatherService {
    String getMetar(String icao);
    MetarVO formatMetar(String code) throws ParseException;
    String getAtis(String icao) throws ParseException;
}
