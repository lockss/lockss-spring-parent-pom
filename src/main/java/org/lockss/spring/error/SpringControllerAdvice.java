/*
 * Copyright (c) 2018, Board of Trustees of Leland Stanford Jr. University,
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.lockss.spring.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(produces = "application/vnd.error+json")
public class SpringControllerAdvice {

  private static Logger log =
      LoggerFactory.getLogger(SpringControllerAdvice.class);

  /**
   * Handles the exception thrown when the request handler cannot generate a response that is acceptable by the client.
   */
  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public ResponseEntity<RestResponseErrorBody> handler(final HttpMediaTypeNotAcceptableException e) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new ResponseEntity<>(new RestResponseErrorBody(e.getMessage(), e.getClass().getSimpleName()), headers,
        HttpStatus.NOT_ACCEPTABLE);
  }

  /**
   * Handles the exception thrown when a client POSTs, PUTs, or PATCHes content of a type not supported by request
   * handler.
   */
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<RestResponseErrorBody> handler(final HttpMediaTypeNotSupportedException e) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new ResponseEntity<>(new RestResponseErrorBody(e.getMessage(), e.getClass().getSimpleName()), headers,
        HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  /**
   * Handles the exception thrown when an endpoint does not support the request HTTP method.
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<RestResponseErrorBody> handler(final HttpRequestMethodNotSupportedException e) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new ResponseEntity<>(new RestResponseErrorBody(e.getMessage(), e.getClass().getSimpleName()), headers,
        HttpStatus.METHOD_NOT_ALLOWED);
  }

  /**
   * Handles the exception thrown when there was an error parsing a HTTP request by a {@link HttpMessageConverter}.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<RestResponseErrorBody> handler(final HttpMessageNotReadableException e) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new ResponseEntity<>(new RestResponseErrorBody(e.getMessage(), e.getClass().getSimpleName()), headers,
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles the exception thrown when a request parameter has failed validation (e.g., malformed).
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RestResponseErrorBody> handler(final MethodArgumentNotValidException e) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new ResponseEntity<>(new RestResponseErrorBody(e.getMessage(), e.getClass().getSimpleName()), headers,
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles the exception thrown when a required request parameter is missing from the request.
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<RestResponseErrorBody> handler(final MissingServletRequestParameterException e) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new ResponseEntity<>(new RestResponseErrorBody(e.getMessage(), e.getClass().getSimpleName()), headers,
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles a custom LOCKSS REST service exception.
   *
   * @param lrse A LockssRestServiceException with the details of the problem.
   * @return a ResponseEntity<RestResponseErrorBody> with the error response in
   * JSON format with media type {@code application/vnd.error+json}.
   */
  @ExceptionHandler(LockssRestServiceException.class)
  public ResponseEntity<RestResponseErrorBody> handler(
      final LockssRestServiceException lrse) {

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);

    return new ResponseEntity<>(new RestResponseErrorBody(lrse), responseHeaders,
        lrse.getHttpStatus());
  }

  /**
   * Handles any other unhandled exception as a last resort.
   *
   * @param e An Exception with the exception not handled by other exception
   * handler methods.
   * @return a ResponseEntity<RestResponseErrorBody> with the error response in
   * JSON format with media type {@code application/vnd.error+json}.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<RestResponseErrorBody> defaultHandler(Exception e) {
    log.error("Caught otherwise unhandled exception", e);
    return new ResponseEntity<>(new RestResponseErrorBody(e.getMessage(),
        e.getClass().getSimpleName()), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
