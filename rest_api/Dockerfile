FROM php:7.2-apache

MAINTAINER Daniel Valle <daniel.vallecl@gmail.com>

WORKDIR /var/www/html

COPY . .

RUN apt-get update && apt-get install --yes --no-install-recommends \
    libssl-dev

RUN a2enmod rewrite

RUN pecl install mongodb \
    && docker-php-ext-enable mongodb\
    && docker-php-ext-install bcmath
