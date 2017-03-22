/*
 * Copyright 2015 - 2016 Red Bull Media House GmbH <http://www.redbullmediahouse.com> - all rights reserved.
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

//#event-sourced-actor

//#event-sourced-actor
package japi;

/** Outer class allows many inner classes to be defined in one file */
public class Messages {
  // Commands
  public class Print {}

  public class Append {
    public final String entry;

    public Append(String entry) {
      this.entry = entry;
    }
  }

  // Command replies
  public class AppendSuccess {
    public final String entry;

    public AppendSuccess(String entry) {
      this.entry = entry;
    }
  }

  public class AppendFailure {
    public final Throwable cause;

    public AppendFailure(Throwable cause) {
      this.cause = cause;
    }
  }

  // Events
  public class Appended {
    public final String entry;

    public Appended(String entry) {
      this.entry = entry;
    }
  }
}
//#
