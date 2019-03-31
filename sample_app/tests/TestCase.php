<?php

namespace Tests;

use Illuminate\Support\Facades\Artisan;
use Illuminate\Foundation\Testing\DatabaseMigrations;
use Illuminate\Foundation\Testing\TestCase as BaseTestCase;

abstract class TestCase extends BaseTestCase
{
    use CreatesApplication, DatabaseMigrations;

    protected $faker;

    /**
     * setUp
     *
     * @return void
     */
    public function setUps()
    {
        parent::setUp();
        Artisan::call('db:seed');
    }
}

