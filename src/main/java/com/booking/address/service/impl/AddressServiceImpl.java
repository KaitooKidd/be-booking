package com.booking.address.service.impl;

import org.springframework.stereotype.Service;

import com.booking.address.repository.AddressRepository;
import com.booking.address.service.AddressService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@SuppressWarnings("all")
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
}
