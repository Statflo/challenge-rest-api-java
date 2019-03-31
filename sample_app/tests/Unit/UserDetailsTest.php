<?php

namespace Tests\Unit;

use Tests\TestCase;
use App\UserDetails;

class UserDetailsTest extends TestCase
{

    /**
     * @test
     *
     * List of users
     */
    public function testUserListedCorrectly() {
        factory(UserDetails::class)->create([
            'name' => 'Mark Kelvin',
            'role' => 'Developer'
        ]);

        factory(UserDetails::class)->create([
            'name' => 'Stew Edward',
            'role' => 'Manager'
        ]);

        $this->json('GET', '/api/user-details')
            ->assertStatus(200)
            ->assertJson([
                [ 'name' => 'Mark Kelvin', 'role' => 'Developer' ],
                [ 'name' => 'Stew Edward', 'role' => 'Manager' ]
            ])
            ->assertJsonStructure([
                [
                    'id',
                    'name',
                    'role',
                    'created_at',
                    'updated_at',
                ],
            ]);
    }

    /**
     * @test
     *
     * Create user
     */
    public function testUserCreatedCorrectly() {
        $newUser = [
            'name' => 'Mark Kelvin',
            'role' => 'Developer',
        ];

        $this->json('POST', '/api/user-details', $newUser)
            ->assertStatus(200)
            ->assertJson([
                    'id' => 1,
                    'name' => 'Mark Kelvin',
                    'role' => 'Developer',
                ]);
    }

    /**
     * @test
     *
     * Update user
     */
    public function testUsersAreUpdatedCorrectly() {
        $user = factory(UserDetails::class)->create([
            'name' => 'Mark Kelvin',
            'role' => 'Developer',
        ]);

        $updateUser = [
            'name' => 'Mark Edward',
            'role' => 'Manager',
        ];

        $this->json('PUT', '/api/user-details/' . $user->id, $updateUser)
            ->assertStatus(200)
            ->assertJson([
                'id' => 1,
                'name' => 'Mark Edward',
                'role' => 'Manager'
            ]);
    }

    /**
     * @test
     *
     * Delete user
     */
    public function testUsersAreDeletedCorrectly() {
        $user = factory(UserDetails::class)->create([
            'name' => 'Mark Edward',
            'role' => 'Manager',
        ]);

        $this->json('DELETE', '/api/user-details/' . $user->id)
            ->assertStatus(204);
    }
}
