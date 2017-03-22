/*
 * Copyright 2015 - 2017 Red Bull Media House GmbH <http://www.redbullmediahouse.com> and Mike Slinn - all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sapi

//#event-sourced-actor
sealed trait EsaAlgebra

// Commands
case object Print extends EsaAlgebra
case class Append(entry: String) extends EsaAlgebra

// Command replies
case class AppendFailure(cause: Throwable) extends EsaAlgebra
case class AppendSuccess(entry: String) extends EsaAlgebra

// Event
case class Appended(entry: String) extends EsaAlgebra
//#

import com.rbmhtechnology.eventuate.VectorTime
//#conditional-requests
case class AppendSuccessWithTimestamp(entry: String, updateTimestamp: VectorTime)
//#
