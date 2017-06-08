import { SpringbootAngular2Page } from './app.po';

describe('springboot-angular2 App', function() {
  let page: SpringbootAngular2Page;

  beforeEach(() => {
    page = new SpringbootAngular2Page();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
