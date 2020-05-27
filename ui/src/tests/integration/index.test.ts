import 'expect-puppeteer';
// const puppeteer = require('puppeteer');

// beforeAll(async() => {
//   browser = await puppeteer.launch({
//     args: [
//       // Required for Docker version of Puppeteer
//       '--no-sandbox',
//       '--disable-setuid-sandbox',
//       // This will write shared memory files into /tmp instead of /dev/shm,
//       // because Docker’s default for /dev/shm is 64MB
//       '--disable-dev-shm-usage'
//     ]
//   })

//   const browserVersion = await browser.version()
//   console.log(`Started ${browserVersion}`)
// })

// beforeEach(async() => {
//   page = await browser.newPage()
// })

// afterEach(async() => {
//   await page.close()
// })

// afterAll(async() => {
//   await browser.close()
// })

describe('App', () => {
  beforeAll(async() => {
    await page.goto('http://ui:3000/');
  });

  it('renders', async() => {
    await expect(page).toMatch('Welcome to Abstract Memery');
  })
})