# Roadmap
## Urgent
- [x] public requestRide (TODO 1)
- [x] public requestTaxi (TODO 2)
- [x] public bookTaxi (TODO 3)
- [x] Random trustworthiness generation after each ride (TODO 4)
- [x] public getNewRating (TODO 6)
- [x] Implement Taxi as an abstract class (TODO 5)
- [x] Implement a (fifo) queue for taxis that support it
- [x] Log new rides(Client)
- [x] Log new rides(Taxi)
- [x] Log new rides(Driver)
- [x] Implement the Van class
- [x] Implement the Lightweight class
- [x] Add origin of a trip(Travel)
- [x] Predict time until client and until destiny after requesting a ride(Taxi)
- [x] Predict cost of the trip(Taxi)
- [x] Add time taken/predicted of a trip(Travel)
- [x] Request rides taken in a period of time(Account/Taxi)
- [x] Consult the total profit of a certain taxi/company in a certain time frame(Taxi/Company)
- [x] Create a new user(App)
- [x] Authentication via credentials(App)
- [x] Basic Terminal Menu(Menu?)
- [x] Load and save application status in a file
- [x] Check for config/saved file at startup
- [x] Fetch top 10/5 clients/drivers
- [x] Output travel registers
- [x] Associate a driver with a taxi if it's an independent driver
- [ ] Dispatch queue when there are reservations
- [x] Deny ride requests when there are still reservations
- [ ] Calculate average punctuality after each ride 
- [x] Charge a certain amount based on the difference between predicted/real time of a ride 
- [ ] Fix bug that rounds every trip effective/real time to 0
- [ ] Generate initial config to allow testing on presentation
- [ ] Review code modularity
- [ ] Finish report

## Next

## Additional Features
- [ ] Change time that a ride takes according to (random) factors such as the weather, traffic, etc
- [ ] Company class which consists of a groups of drivers and taxis
- [ ] GUI for the app
- [ ] Attribute a taxi to a driver when requested by a driver
