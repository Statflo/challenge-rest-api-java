<?php

use Illuminate\Database\Seeder;
use App\UserDetails;

class UserDetailsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        // Let's truncate our existing records to start from scratch.
        UserDetails::truncate();

        $faker = \Faker\Factory::create();

        // And now, let's create a few articles in our database:
        for ($i = 0; $i < 20; $i++) {
            UserDetails::create([
                'name' => $faker->name,
                'role' => $faker->word,
            ]);
        }
    }
}
