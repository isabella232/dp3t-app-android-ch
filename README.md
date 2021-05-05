# LibreCHovid

[![License: MPL 2.0](https://img.shields.io/badge/License-MPL%202.0-brightgreen.svg)](https://github.com/c4dt/dp3t-app-android-ch/blob/microg-nearby/LICENSE)
![Android Build](https://github.com/c4dt/dp3t-app-android-ch/actions/workflows/nogaen.yml/badge.svg)

LibreCHovid is a fork of the official
[SwissCovid](https://www.bag.admin.ch/bag/en/home/krankheiten/ausbrueche-epidemien-pandemien/aktuelle-ausbrueche-epidemien/novel-cov/swisscovid-app-und-contact-tracing.html)
contact tracing app for Switzerland. It does not rely on the proprietary
Google-Apple Exposure Notification framework, but uses instead the compatible
microG free implementation. It will therefore run on devices without Google
Mobile Services.

The functionality of the application is the same as the original. However, as
it does not have the privileges granted to GMS, its ability to run reliably in
the background may be affected (see [here](https://dontkillmyapp.com/) for some
workarounds). Our internal tests have shown it to behave well on various
systems, but your mileage may vary.

Please note that this fork is **not supported by the Swiss authorities**.

[<img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
     alt="Get it on F-Droid"
     height="80">](https://f-droid.org/packages/ch.corona.tracing/)
