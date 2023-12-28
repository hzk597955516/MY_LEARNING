test = {
  'name': 'contains?',
  'points': 1,
  'suites': [
    {
      'cases': [
        {
          'code': r"""
          scm> (contains? odds 3)   ; #t or #f
          #t
          """,
          'hidden': False,
          'locked': False
        },
        {
          'code': r"""
          scm> (contains? odds 9)   ; #t or #f
          #t
          """,
          'hidden': False,
          'locked': False
        },
        {
          'code': r"""
          scm> (contains? odds 6)   ; #t or #f
          #f
          """,
          'hidden': False,
          'locked': False
        }
      ],
      'scored': True,
      'setup': r"""
      scm> (load 'hw07)
      scm> (define odds (list 3 5 7 9))
      """,
      'teardown': '',
      'type': 'scheme'
    }
  ]
}
