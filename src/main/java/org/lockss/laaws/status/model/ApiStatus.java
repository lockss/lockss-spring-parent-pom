/*

Copyright (c) 2000-2019 Board of Trustees of Leland Stanford Jr. University,
all rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package org.lockss.laaws.status.model;

/**
 * Representation of the status of a REST web service.
 */
public class ApiStatus {

  /**
   * The version of the REST web service.
   */
  private String version = null;

  /**
   * An indication of whether the REST web service is ready to process requests.
   */
  private Boolean ready = Boolean.FALSE;

  /**
   * Provides the version of the REST web service.
   * 
   * @return a String with the version of the REST web service.
   */
  public String getVersion() {
    return version;
  }

  /**
   * Saves the version of the REST web service.
   * 
   * @param version
   *          A String with the version of the REST web service.
   * @return an ApiStatus with this object.
   */
  public ApiStatus setVersion(String version) {
    this.version = version;
    return this;
  }

  /**
   * Provides an indication of whether the REST web service is available.
   * 
   * @return a Boolean with the indication of whether the REST web service is
   *         available.
   */
  public Boolean isReady() {
    return ready;
  }

  /**
   * Saves the indication of whether the REST web service is available.
   * 
   * @param ready
   *          A Boolean with the indication of whether the REST web service is
   *          available.
   * @return an ApiStatus with this object.
   */
  public ApiStatus setReady(Boolean ready) {
    this.ready = ready;
    return this;
  }

  @Override
  public String toString() {
    return "[ApiStatus version=" + version + ", ready=" + ready + "]";
  }
}
